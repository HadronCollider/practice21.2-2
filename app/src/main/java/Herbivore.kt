import java.lang.Math.abs
import kotlin.random.Random
import kotlin.random.nextInt

/* Class Herbivore (травоядное)
 * position - координаты животного (количество клеток от левого верхнего угла)
 * fieldOfView - область видмости (количество клеток от животного во все стороны (в т.ч. по диагонали)
 * speed - скорость животного (количество клеток, которое может пройти животное за один ход)
 */
class Herbivore(position: Point, fieldOfView: Int, speed: Int): Animal(position, fieldOfView, speed, 1F) {
    val isItPredator: Boolean = false            // Для отрисовки
    /* Функция, реализующая поведение для животного (куда животное пойдет на следующем ходу)
     * Возвращает координату уничтоженного растения или (-1, -1), если этого не произошло
     */
    override fun move(field: ArrayList<ArrayList<Int>>): Point {
        var isMoved = false

        for (i in 0 until field.size)
            if (!isMoved) {
                for (j in 0 until field.size)
                    if (Point(j, i) != position) {
                        if (field[i][j] == 1) {
                            if (kotlin.math.abs(i - position.posY) <= fieldOfView && kotlin.math.abs(j - position.posX) <= fieldOfView &&
                                field[position.posY + (i - position.posY) / kotlin.math.abs(i - position.posY) * speed][position.posX + (j - position.posX) / kotlin.math.abs(j - position.posX) * speed] == 0) {
                                // Если растение находится в поле видимости
                                position = Point(
                                    position.posX + (j - position.posX) / kotlin.math.abs(j - position.posX) * speed,
                                    position.posY + (i - position.posY) / kotlin.math.abs(i - position.posY) * speed
                                )
                                isMoved = true
                                if (field[position.posY][position.posX] == 1) {
                                    currentAmountOfFood++
                                    return Point(position.posX, position.posY)
                                }
                            }
                        }
                        if (field[i][j] == 3)
                            if (kotlin.math.abs(i - position.posY) <= fieldOfView && kotlin.math.abs(j - position.posX) <= fieldOfView &&
                                field[position.posY + (i - position.posY) / kotlin.math.abs(i - position.posY) * speed][position.posX + (j - position.posX) / kotlin.math.abs(j - position.posX) * speed] == 0 &&
                                position.posX - (j - position.posX) / kotlin.math.abs(j - position.posX) * speed < field[0].size &&
                                position.posY - (i - position.posY) / kotlin.math.abs(i - position.posY) * speed < field[0].size &&
                                position.posX - (j - position.posX) / kotlin.math.abs(j - position.posX) * speed >= 0 &&
                                position.posY - (i - position.posY) / kotlin.math.abs(i - position.posY) * speed >= 0) {
                                // Если хищник находится в поле видимости
                                position = Point(
                                    position.posX - (j - position.posX) / kotlin.math.abs(j - position.posX) * speed,
                                    position.posY - (i - position.posY) / kotlin.math.abs(i - position.posY) * speed
                                )
                                isMoved = true
                                break
                            }
                    }
            }

        if (!isMoved)
        {
            val dx = Random.nextInt(-1..1)
            val dy = Random.nextInt(-1..1)
            if (position.posX + dx * speed < field[0].size &&
                position.posX + dx * speed >= 0 &&
                position.posY + dy * speed < field[0].size &&
                position.posY + dy * speed > 0) {

                position = Point(
                    position.posX + dx * speed,
                    position.posY + dy * speed
                )
            }
        }
        return Point(-1, -1)
    }
}