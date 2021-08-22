package com.example.abl.model

import kotlinx.serialization.Serializable

data class LovResponse(
    val company_lead_source: List<CompanyLeadSource>,
    val company_lead_status: List<CompanyLeadStatu>,
    val company_products: List<CompanyProduct>,
    val company_visit_status: List<CompanyVisitStatu>
)