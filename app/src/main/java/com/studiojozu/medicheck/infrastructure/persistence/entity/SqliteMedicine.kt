package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.medicine.*

@Entity(tableName = "medicine")
open class SqliteMedicine(medicineId: MedicineIdType) {
    class Builder {
        lateinit var medicine: Medicine

        fun build(): SqliteMedicine {
            val sqliteMedicine = SqliteMedicine(medicineId = medicine.medicineId)
            sqliteMedicine.medicineName = medicine.medicineName
            sqliteMedicine.medicineTakeNumber = medicine.medicineTakeNumber
            sqliteMedicine.medicineUnitId = medicine.medicineUnit.medicineUnitId
            sqliteMedicine.medicineDateNumber = medicine.medicineDateNumber
            sqliteMedicine.medicineStartDatetime = medicine.medicineStartDatetime
            sqliteMedicine.medicineInterval = medicine.medicineInterval
            sqliteMedicine.medicineIntervalMode = medicine.medicineIntervalMode
            sqliteMedicine.medicinePhoto = medicine.medicinePhoto
            sqliteMedicine.medicineNeedAlarm = medicine.medicineNeedAlarm
            sqliteMedicine.medicineDeleteFlag = medicine.medicineDeleteFlag

            return sqliteMedicine
        }
    }

    companion object {
        fun build(f: Builder.() -> Unit): SqliteMedicine {
            val builder = Builder()
            builder.f()
            return builder.build()
        }
    }

    /** ID  */
    @Suppress("CanBePrimaryConstructorProperty")
    @PrimaryKey
    @ColumnInfo(name = "medicine_id")
    var medicineId: MedicineIdType = medicineId

    /** 名前  */
    @ColumnInfo(name = "medicine_name")
    var medicineName: MedicineNameType = MedicineNameType()

    /** 服用数  */
    @ColumnInfo(name = "medicine_take_number")
    var medicineTakeNumber: MedicineTakeNumberType = MedicineTakeNumberType()

    /** 服用数 単位  */
    @ColumnInfo(name = "medicine_unit_id")
    var medicineUnitId: MedicineUnitIdType = MedicineUnitIdType()

    /** 服用日数  */
    @ColumnInfo(name = "medicine_date_number")
    var medicineDateNumber: MedicineDateNumberType = MedicineDateNumberType()

    /** 服用開始日時  */
    @ColumnInfo(name = "medicine_start_datetime")
    var medicineStartDatetime: MedicineStartDatetimeType = MedicineStartDatetimeType()

    /** 服用間隔  */
    @ColumnInfo(name = "medicine_interval")
    var medicineInterval: MedicineIntervalType = MedicineIntervalType()

    /** 服用間隔タイプ  */
    @ColumnInfo(name = "medicine_interval_mode")
    var medicineIntervalMode: MedicineIntervalModeType = MedicineIntervalModeType()

    /** 薬の写真  */
    @ColumnInfo(name = "medicine_photo")
    var medicinePhoto: MedicinePhotoType = MedicinePhotoType()

    /** アラーム要否  */
    @ColumnInfo(name = "medicine_need_alarm")
    var medicineNeedAlarm: MedicineNeedAlarmType = MedicineNeedAlarmType()

    /** 削除フラグ  */
    @ColumnInfo(name = "medicine_delete_flag")
    var medicineDeleteFlag: MedicineDeleteFlagType = MedicineDeleteFlagType()
}
