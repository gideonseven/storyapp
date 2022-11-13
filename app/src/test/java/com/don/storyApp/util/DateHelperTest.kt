package com.don.storyApp.util

import org.junit.Assert
import org.junit.Test

/**
 * Created by gideon on 13 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class DateHelperTest {

    private val normalDateFormat = "dd MMMM yyyy"
    private val invalidDateFormat = "dddd MMMM yyyy"
    private val actualDate = "13 November 2022"
    private val correctRfcDate = "2022-11-13T10:33:39.163Z"
    private val wrongRfcDate = "202-11-13T10:33:39.163Z"
    private val dateHelper = DateHelper()


    @Test
    fun `given wrong date format should throw error or failed`() {

        Assert.assertEquals(
            dateHelper.formatStringFromRFC3339Format(correctRfcDate, invalidDateFormat),
            actualDate
        )
    }

    @Test
    fun `given wrong rfcDate should throw error or failed`() {

        Assert.assertEquals(
            dateHelper.formatStringFromRFC3339Format(wrongRfcDate, normalDateFormat),
            actualDate
        )
    }

    @Test
    fun `given correct rfcDate should throw error or failed`() {

        Assert.assertEquals(
            dateHelper.formatStringFromRFC3339Format(correctRfcDate, normalDateFormat),
            actualDate
        )
    }
}