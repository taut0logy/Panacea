package com.project.panacea

object HeartRateData {
    private var systolicPressure: String = "120"
    private var diastolicPressure: String = "80"

    fun getSystolicPressure(): String {
        return systolicPressure
    }

    fun setSystolicPressure(newPressure: String) {
        systolicPressure = newPressure
    }

    fun getDiastolicPressure(): String {
        return diastolicPressure
    }

    fun setDiastolicPressure(newPressure: String) {
        diastolicPressure = newPressure
    }

}
