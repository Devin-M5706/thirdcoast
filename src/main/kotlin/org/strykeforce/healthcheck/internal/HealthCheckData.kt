package org.strykeforce.healthcheck.internal

import com.ctre.phoenix.motorcontrol.can.BaseTalon
import com.ctre.phoenix6.hardware.TalonFX
import com.ctre.phoenix6.hardware.TalonFXS

data class DiagnosticLimits(
    val currentMin: Double = 0.0, val currentMax: Double = 0.0, val speedMin: Double = 0.0, val speedMax: Double = 0.0
)

class TalonHealthCheckData(val case: Int, private val talon: BaseTalon) {
    val deviceId = talon.deviceID
    val timestamp: MutableList<Long> = mutableListOf()
    val voltage: MutableList<Double> = mutableListOf()
    val position: MutableList<Double> = mutableListOf()
    val speed: MutableList<Double> = mutableListOf()
    val supplyCurrent: MutableList<Double> = mutableListOf()
    val statorCurrent: MutableList<Double> = mutableListOf()

    fun measure(ts: Long) {
        timestamp.add(ts)
        voltage.add(talon.motorOutputVoltage)
        position.add(talon.selectedSensorPosition)
        speed.add(talon.selectedSensorVelocity)
        supplyCurrent.add(talon.supplyCurrent)
        statorCurrent.add(talon.statorCurrent)
    }

    fun reset() {
        timestamp.clear()
        voltage.clear()
        position.clear()
        speed.clear()
        supplyCurrent.clear()
        statorCurrent.clear()
    }

    val id
        get() = talon.deviceID

    val averageVoltage
        get() = voltage.average()

    val averageSpeed
        get() = speed.average()

    val averageSupplyCurrent
        get() = supplyCurrent.average()

    val averageStatorCurrent
        get() = statorCurrent.average()

}

class P6TalonHealthCheckData(val case: Int, private val talonFx: TalonFX) {
    val deviceId = talonFx.deviceID
    val timestamp : MutableList<Long> = mutableListOf()
    val voltage: MutableList<Double> = mutableListOf()
    val position: MutableList<Double> = mutableListOf()
    val speed: MutableList<Double> = mutableListOf()
    val supplyCurrent: MutableList<Double> = mutableListOf()
    val statorCurrent: MutableList<Double> = mutableListOf()

    fun measure(ts: Long) {
        timestamp.add(ts)
        voltage.add(talonFx.motorVoltage.valueAsDouble)
        position.add(talonFx.position.valueAsDouble)
        speed.add(talonFx.velocity.valueAsDouble)
        supplyCurrent.add(talonFx.supplyCurrent.valueAsDouble)
        statorCurrent.add(talonFx.statorCurrent.valueAsDouble)
    }

    fun reset() {
        timestamp.clear()
        voltage.clear()
        position.clear()
        speed.clear()
        supplyCurrent.clear()
        statorCurrent.clear()
    }

    val id
        get() = talonFx.deviceID

    val averageVoltage
        get() = voltage.average()

    val averageSpeed
        get() = speed.average()

    val averageSupplyCurrent
        get() = supplyCurrent.average()

    val averageStatorCurrent
        get() = statorCurrent.average()
}

class FXSTalonHealthCheckData(val case: Int, private val talonFxs: TalonFXS) {
    val deviceId = talonFxs.deviceID
    val timestamp : MutableList<Long> = mutableListOf()
    val voltage: MutableList<Double> = mutableListOf()
    val position: MutableList<Double> = mutableListOf()
    val speed: MutableList<Double> = mutableListOf()
    val supplyCurrent: MutableList<Double> = mutableListOf()
    val statorCurrent: MutableList<Double> = mutableListOf()

    fun measure(ts: Long) {
        timestamp.add(ts)
        voltage.add(talonFxs.motorVoltage.valueAsDouble)
        position.add(talonFxs.position.valueAsDouble)
        speed.add(talonFxs.velocity.valueAsDouble)
        supplyCurrent.add(talonFxs.supplyCurrent.valueAsDouble)
        statorCurrent.add(talonFxs.statorCurrent.valueAsDouble)
    }

    fun reset() {
        timestamp.clear()
        voltage.clear()
        position.clear()
        speed.clear()
        supplyCurrent.clear()
        statorCurrent.clear()
    }

    val id
        get() = talonFxs.deviceID

    val averageVoltage
        get() = voltage.average()

    val averageSpeed
        get() = speed.average()

    val averageSupplyCurrent
        get() = supplyCurrent.average()

    val averageStatorCurrent
        get() = statorCurrent.average()
}

internal fun DoubleArray.limitsFor(iteration: Int): DiagnosticLimits {
    val index = iteration * 4
    if (size > index + 3) return DiagnosticLimits(
        this[index], this[index + 1], this[index + 2], this[index + 3]
    )
    return DiagnosticLimits()
}