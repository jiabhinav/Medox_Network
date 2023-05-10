package com.app.medoxnetwork.ui.home
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentDepositQRBinding
import com.app.medoxnetwork.utils.Utility
import com.app.medoxnetwork.utils.Utility.showToast
import com.app.medoxnetwork.viewmodel.WalletViewModel
import com.google.gson.Gson
import com.google.zxing.WriterException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DepositQR : BaseFragment() {

    lateinit var binding:FragmentDepositQRBinding

    lateinit var qrcode:QRGEncoder
    private val viewModel: WalletViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (!this::binding.isInitialized)
        {
            binding = FragmentDepositQRBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    fun init()
    {
        binding.depositAddress.setOnClickListener {
            requireContext().copyToClipboard( binding.depositAddress.text.toString())
        }

        binding.mnt.setOnClickListener {
            refreshWallet(1)
        }
        binding.usdt.setOnClickListener {
            refreshWallet(2)
        }


        observeData()
        callAPI()

    }

    fun callAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        viewModel.android_deposit_page(params)
    }

    fun refreshWallet(type:Int)
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        params.put("currency",type.toString())
        viewModel.android_refresh_fund(params)
    }

    fun observeData() {
        viewModel.deposit.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if(it.status==1)
            {
                binding.depositAddress.text=it.result.deposit_address
                createQRCode(it.result.deposit_address)
            }
            else
            {
                showToast(getString(R.string.contecttosupport), 2)
            }

        }

        viewModel.refreshDeposit.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
             showToast(it.result.msg,2)
            if (it.status==1)
               callAPI()

        }


        viewModel.error.observe(requireActivity()) {
            showToast(it, 2)
        }

        viewModel.loading.observe(requireActivity()) {
            loadingProgress(it)
        }

    }


    fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
    }



    fun createQRCode(address:String)
    {
        val manager = requireContext().getSystemService(WINDOW_SERVICE) as WindowManager?

        val display: Display = manager!!.defaultDisplay
        val point = Point()
        display.getSize(point)

        val width: Int = point.x
        val height: Int = point.y

        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        qrcode = QRGEncoder(address, null, QRGContents.Type.TEXT, dimen)

        try {
            val  bitmap = qrcode.encodeAsBitmap()
            binding.qrcode.setImageBitmap(bitmap)
        }
        catch (e: WriterException) {

        }
    }


}