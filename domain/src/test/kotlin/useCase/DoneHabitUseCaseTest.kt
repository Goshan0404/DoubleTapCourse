package useCase

import com.example.domain.useCase.DoneHabitUseCase
import com.example.domain.useCase.DoneState
import com.example.doubletapcourse.domain.model.HabitDomain
import com.example.doubletapcourse.domain.useCase.SaveHabitUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.testng.Assert


class DoneHabitUseCaseTest {
    private lateinit var doneHabitUseCase: DoneHabitUseCase
    private lateinit var saveHabitUseCase: SaveHabitUseCase

    @Before
    fun setUp() {
        saveHabitUseCase = Mockito.mock(SaveHabitUseCase::class.java)
        doneHabitUseCase = DoneHabitUseCase(saveHabitUseCase)
    }

    @Test
    fun `test done of positive habit less future`() {
        runBlocking {
            val habit = HabitDomain("", "", "", 0, 1, 1, 1, 0, 2)
            val result = doneHabitUseCase.invoke(habit)
            Assert.assertTrue(result is DoneState.PositiveHabitDoneLess && result.count == 1)
        }
    }

    @Test
    fun `test done of positive habit more future`() {
        runBlocking {
            val habit = HabitDomain("", "", "", 0, 1, 1, 1, 3, 2)
            val result = doneHabitUseCase.invoke(habit)
            Assert.assertEquals(result, DoneState.PositiveHabitDoneOverFlow)
        }
    }

    @Test
    fun `test done of negative habit less future`() {
        runBlocking {
            val habit = HabitDomain("", "", "", 1, 1, 1, 1, 0, 2)
            val result = doneHabitUseCase.invoke(habit)
            Assert.assertTrue(result is DoneState.NegativeHabitDoneLess && result.count == 1)
        }
    }

    @Test
    fun `test done of negative habit more future`() {
        runBlocking {
            val habit = HabitDomain("", "", "", 1, 1, 1, 1, 3, 2)
            val result = doneHabitUseCase.invoke(habit)
            Assert.assertEquals(result, DoneState.NegativeHabitDoneOverFlow)
        }
    }
}