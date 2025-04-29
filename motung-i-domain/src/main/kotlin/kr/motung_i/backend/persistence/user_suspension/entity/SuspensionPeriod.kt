package kr.motung_i.backend.persistence.user_suspension.entity

import java.time.Period

enum class SuspensionPeriod(
    val description: String,
    val period: Period,
) {
    PERMANENT("영구 정지", Period.ofYears(100)),
    ONE("1일 정지", Period.ofDays(1)),
    SEVEN("7일 정지", Period.ofDays(7)),
    FOURTEEN("14일 정지", Period.ofDays(14)),
    THIRTY("30일 정지", Period.ofDays(30)),
}