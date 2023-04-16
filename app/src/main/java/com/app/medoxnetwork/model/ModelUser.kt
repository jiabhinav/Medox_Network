package com.app.medoxnetwork.model

data class ModelUser(
    val message: String,
    val status: Int,
    val stepStatus: StepStatus,
    val trainingStatus: TrainingStatus,
    val user: User

) {
    data class StepStatus(
        val basicInfoVerificationStatus: Int=0,
        val emailVerificationStatus: Int=0,
        val mobileVerificationStatus: Int=0,
        val trainingVerificationStatus: Int=0
    )

    data class TrainingStatus(
        val bank: Int=0,
        val docs: Int=0,
        val eye: Int=0,
        val face: Int=0,
        val fingerPrint: Int=0,
        val palm: Int=0,
        val social: Int=0,
        val total: Int=0,
        val voice: Int=0
    )

    data class User(
        var bid: Any?=null,
        val city_name: String?=null,
        val correspondenceAddress: String?=null,
        var country: String?=null,
        val countryId: Int?=null,
        val course_type_name: String?=null,
        val dateFormat: String?=null,
        val district_name: String?=null,
        val dob: String?=null,
        var email: String?=null,
        val emailNotification: String?=null,
        val enrolment_id: String?=null,
        val fatherName: String?=null,
        val first_name: String?=null,
        val gender: String?=null,
        val id: Int=0,
        val imagePerson: String?=null,
        val institute_name: String?=null,
        val kycstatus: String?=null,
        val last_name: String?=null,
        val middle_name: String?=null,
        val mobile: String?=null,
        var name: String?=null,
        val permanentAddress: String?=null,
        val pushNotification: String?=null,
        val sbid: Any?=null,
        val smsNotification: String?=null,
        val state_name: String?=null,
        var token: String="",
        val trade_name: String?=null,
        val trainingProgress: Int=0,
        val training_center_id: String?=null,
        val training_center_name: String?=null,
        val type: String?=null,
        val url: String?=null,
        val user_type: String?=null,
        var userId: String="",
        var role: String="",
        var Jwttoken:String=""
    )
}