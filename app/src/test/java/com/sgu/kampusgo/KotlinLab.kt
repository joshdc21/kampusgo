package com.sgu.kampusgo
// ^^^ Must match MainActivity.kt. Put this file at:
//     app/src/test/java/com/sgu/kampusgo/KotlinLab.kt
// (use YOUR package folders if they differ)

/**
 * Lab 02 — Kotlin essentials
 *
 * Read the handout links first. Replace every TODO(). Do not use !!.
 *
 * RUN: green triangle next to allChecks() — not the toolbar Play that opens the phone.
 */

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * data class = a class made to hold data.
 * Each Student has fields you read with a dot: student.name, student.gpa, …
 * advisor is String?  →  it may be a String OR null (no advisor).
 */
data class Student(
    val name: String,
    val npm: String,
    val angkatan: Int,
    val gpa: Double,
    val advisor: String?,
)

val sampleStudents: List<Student> = listOf(
    Student("Budi Santoso", "001", 2024, 3.81, "Ibu Wati"),
    Student("Siti Rahma", "002", 2024, 3.20, "Ibu Wati"),
    Student("Andi Wijaya", "003", 2023, 2.70, null),
    Student("Dewi Lestari", "004", 2025, 3.95, "Pak Eko"),
    Student("Raka Putra", "005", 2023, 3.55, "Pak Eko"),
    Student("Maya Chen", "006", 2025, 3.40, null),
    Student("Fajar Nugroho", "007", 2024, 3.62, "Ibu Wati"),
)

/**
 * Keep students with GPA >= 3.5, highest GPA first.
 * Read: kotlinlang.org/docs/collection-filtering.html
 * and collection-transformations (sortedByDescending).
 */
/** Students with GPA >= 3.5, sorted by GPA descending. */
fun deanList(students: List<Student>): List<Student> {
    return students.filter { student -> student.gpa > 3.50 }.sortedByDescending { student -> student.gpa }
}

/** Average GPA. Empty list returns 0.0. Do not crash. */
fun averageGpa(students: List<Student>): Double {
    return students.map { student -> student.gpa }.ifEmpty { return 0.0 }.average()
}

/** Map angkatan -> names in that cohort. */
fun namesByAngkatan(students: List<Student>): Map<Int, List<String>> {
    return students.groupBy { student -> student.angkatan }.mapValues{(_, students) -> students.map{student -> student.name}}
}

/** NPMs that have no advisor. */
fun missingAdvisorNpms(students: List<Student>): List<String> {
    return students.filter { student -> student.advisor == null }.map { student -> student.npm }
}

/**
 * Letter from GPA:
 * 3.5+ A, 3.0+ B, 2.0+ C, else D.
 */
fun letterGrade(gpa: Double): Char {
    return when {
        gpa >= 3.5 -> 'A'
        gpa >= 3.0 -> 'B'
        gpa >= 2.0 -> 'C'
        else -> 'D'
    }
}

/** Do not change this test. Fill the five functions until it PASSES. */
class KotlinLabTest {

    @Test
    fun allChecks() {
        assertEquals("deanList size", 4, deanList(sampleStudents).size)
        assertEquals(
            "deanList first",
            "Dewi Lestari",
            deanList(sampleStudents).first().name,
        )
        assertEquals(
            "average roughly 3.46",
            3,
            averageGpa(sampleStudents).toInt(),
        )
        assertEquals("empty average", 0.0, averageGpa(emptyList()), 0.0)
        assertEquals(
            "angkatan keys",
            setOf(2023, 2024, 2025),
            namesByAngkatan(sampleStudents).keys,
        )
        assertEquals(
            "names in 2024",
            listOf("Budi Santoso", "Siti Rahma", "Fajar Nugroho"),
            namesByAngkatan(sampleStudents)[2024],
        )
        assertEquals(
            "missing advisors",
            listOf("003", "006"),
            missingAdvisorNpms(sampleStudents),
        )
        assertEquals("letter 3.81", 'A', letterGrade(3.81))
        assertEquals("letter 3.5", 'A', letterGrade(3.5))
        assertEquals("letter 3.0", 'B', letterGrade(3.0))
        assertEquals("letter 2.70", 'C', letterGrade(2.70))
        assertEquals("letter 1.9", 'D', letterGrade(1.9))
    }
}
