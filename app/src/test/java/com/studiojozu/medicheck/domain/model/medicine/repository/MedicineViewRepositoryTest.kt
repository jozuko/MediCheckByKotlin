package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class MedicineViewRepositoryTest : ATestParent() {

    @Inject
    lateinit var dao: MedicineViewRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun findAll() {
        // addData
        val medicineUnit = getMedicineUnit("錠")
        val medicine = getMedicine("メルカゾール", medicineUnit.mMedicineUnitId.dbValue)
        dao.insert(medicine)

        // findAll
        val medicines = dao.findAllNoTimetable()
        Assert.assertEquals(1, medicines.size)
        assert(medicine, medicines[0])

        // delete data
        dao.delete(medicine)

        // findAll
        Assert.assertEquals(0, dao.findAllNoTimetable().size)
    }

    @Test
    @Throws(Exception::class)
    fun findByMedicineId() {
        // addData
        val medicineUnit = getMedicineUnit("錠")
        val medicine = getMedicine("メルカゾール", medicineUnit.mMedicineUnitId.dbValue)
        dao.insert(medicine)

        // findByMedicineId
        val actual = dao.findByMedicineId(medicine.mMedicineId)!!
        assert(medicine, actual)

        // select no data
        Assert.assertNull(dao.findByMedicineId(medicineId = MedicineIdType("unknown id")))

        // delete data
        dao.delete(medicine)
    }

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(mMedicineName = MedicineNameType(name),
                    mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType(unitId)))

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(mMedicineUnitValue = MedicineUnitValueType(value))

    private fun assert(expectMedicine: Medicine, actual: Medicine) {
        Assert.assertEquals(expectMedicine.mMedicineId, actual.mMedicineId)
        Assert.assertEquals(expectMedicine.mMedicineName, actual.mMedicineName)
        Assert.assertEquals(expectMedicine.mMedicineTakeNumber, actual.mMedicineTakeNumber)
        Assert.assertEquals(expectMedicine.mMedicineDateNumber, actual.mMedicineDateNumber)
        Assert.assertEquals(expectMedicine.mMedicineStartDatetime, actual.mMedicineStartDatetime)
        Assert.assertEquals(expectMedicine.mMedicineInterval, actual.mMedicineInterval)
        Assert.assertEquals(expectMedicine.mMedicineIntervalMode, actual.mMedicineIntervalMode)
        Assert.assertEquals(expectMedicine.mMedicinePhoto, actual.mMedicinePhoto)
        Assert.assertEquals(expectMedicine.mMedicineNeedAlarm, actual.mMedicineNeedAlarm)
        Assert.assertEquals(expectMedicine.mMedicineDeleteFlag, actual.mMedicineDeleteFlag)

        Assert.assertEquals(expectMedicine.mMedicineUnit.mMedicineUnitId, actual.mMedicineUnit.mMedicineUnitId)
        Assert.assertEquals(expectMedicine.mMedicineUnit.mMedicineUnitValue, expectMedicine.mMedicineUnit.mMedicineUnitValue)
        Assert.assertEquals(expectMedicine.mMedicineUnit.mMedicineUnitDisplayOrder, expectMedicine.mMedicineUnit.mMedicineUnitDisplayOrder)
    }
}