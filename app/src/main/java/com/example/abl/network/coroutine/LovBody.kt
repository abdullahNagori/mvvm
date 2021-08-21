package com.example.abl.network.coroutine

import com.example.abl.model.CompanyLeadSource
import com.example.abl.model.CompanyLeadStatu
import com.example.abl.model.CompanyProduct
import com.example.abl.model.CompanyVisitStatu

data class LovBody(
    val company_lead_source: List<CompanyLeadSource>,
    val company_lead_status: List<CompanyLeadStatu>,
    val company_products: List<CompanyProduct>,
    val company_visit_status: List<CompanyVisitStatu>
)