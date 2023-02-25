package lotto.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class LottoTest {
    @Test
    fun `갖고 있는 로또와 당첨 번호 6개가 모두 같으면 6을 반환한다`() {
        // given
        val lotto = Lotto.from(
            generateLotto(listOf(1, 2, 3, 4, 5, 6))
        )
        val winLotto = WinLotto(
            Lotto.from(
                generateLotto(listOf(1, 2, 3, 4, 5, 6))
            ),
            LottoNumber(7)
        )
        // when
        val actual = lotto.determineCountOfMatch(winLotto.winNumber)
        // then
        assertThat(actual).isEqualTo(6)
    }

    @Test
    fun `갖고 있는 로또와 당첨 보너스 번호가 같으면 true를 반환한다`() {
        // given
        val lotto = Lotto.from(
            generateLotto(listOf(1, 2, 3, 4, 5, 6))
        )
        val winLotto = WinLotto(
            Lotto.from(
                generateLotto(listOf(11, 12, 13, 14, 15, 16))
            ),
            LottoNumber(1)
        )
        // when
        val actual = lotto.determineMatchBonus(winLotto.bonus)
        // then
        assertThat(actual).isEqualTo(true)
    }

    @MethodSource("provideLottoCountNotSix")
    @ParameterizedTest
    fun `로또 번호가 6개가 아니면 예외가 발생한다`(lotto: List<LottoNumber>) {
        assertThrows<IllegalArgumentException> { Lotto.from(lotto) }
    }

    @MethodSource("provideDuplicateLotto")
    @ParameterizedTest
    fun `로또 번호가 중복되면 예외가 발생한다`(lotto: List<LottoNumber>) {
        assertThrows<IllegalArgumentException> { Lotto.from(lotto) }
    }

    companion object {
        @JvmStatic
        fun provideDuplicateLotto(): List<Arguments> {
            return listOf(
                Arguments.of(
                    generateLotto(listOf(1, 1, 2, 3, 4, 5))
                ),
                Arguments.of(
                    generateLotto(listOf(41, 41, 42, 43, 44, 45))
                )
            )
        }

        @JvmStatic
        fun provideLottoCountNotSix(): List<Arguments> {
            return listOf(
                Arguments.of(
                    generateLotto(listOf(1, 2, 3, 4, 5))
                ),
                Arguments.of(
                    generateLotto(listOf(1, 2, 3, 4, 5, 6, 7))
                )
            )
        }

        fun generateLotto(lottoNumber: List<Int>): List<LottoNumber> = lottoNumber.map(::LottoNumber)
    }
}
