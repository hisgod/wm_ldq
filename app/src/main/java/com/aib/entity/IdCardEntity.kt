package com.aib.entity

/**
 * 身份证上传信息实体
 */
data class IdCardEntity(
        val addr_card: String,
        val be_idcard: String,
        val branch_issued: String,
        val classify: String,
        val date_birthday: String,
        val flag_sex: String,
        val id_name: String,
        val id_no: String,
        val result_auth: String,
        val result_status: String,
        val ret_code: String,
        val ret_msg: String,
        val risk_tag: RiskTag,
        val start_card: String,
        val state_id: String,
        val url_backcard: String,
        val url_frontcard: String,
        val url_photoget: String,
        val url_photoliving: String
) {
    data class RiskTag(
            val living_attack: String
    )
}

