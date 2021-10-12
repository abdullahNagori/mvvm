package com.uhfsolutions.abl.network.coroutine

import com.uhfsolutions.abl.model.lov.CompanyLeadSource
import com.uhfsolutions.abl.model.lov.CompanyLeadStatu
import com.uhfsolutions.abl.model.lov.CompanyProduct
import com.uhfsolutions.abl.model.lov.CompanyVisitStatu

data class LovBody(
    val company_lead_source: List<CompanyLeadSource>,
    val company_lead_status: List<CompanyLeadStatu>,
    val company_products: List<CompanyProduct>,
    val company_visit_status: List<CompanyVisitStatu>
)