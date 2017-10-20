package com.studiojozu.common.exception

/**
 * DBアクセスでエラーが発生した場合の例外
 */
class DatabaseException(message: String) : RuntimeException(message)
