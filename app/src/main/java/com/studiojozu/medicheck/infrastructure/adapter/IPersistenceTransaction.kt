package com.studiojozu.medicheck.infrastructure.adapter

/**
 * 永続化層のトランザクションを定義するI/F
 */
interface IPersistenceTransaction {
    fun beginTransaction()

    fun commit()

    fun rollback()
}
