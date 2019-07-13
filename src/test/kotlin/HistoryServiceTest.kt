package services

import data.HundenDB
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import models.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class HistoryServiceTest {

    private val dataBaseMock = mockk<HundenDB>()
    private val service = HistoryService(db = dataBaseMock)

}