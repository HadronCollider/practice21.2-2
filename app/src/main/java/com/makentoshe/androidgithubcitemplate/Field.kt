package com.makentoshe.androidgithubcitemplate

import android.os.Handler
import android.os.Looper

class Field(
    fieldViewSet: FieldView
) {
    private var fieldView = fieldViewSet

    private val predatorsList = mutableListOf<PredatorV>()
    private val herbivoresList = mutableListOf<HerbivoreV>()
    private val plantsList = mutableListOf<PlantV>()

    private val deathPlantsIndices = mutableListOf<Int>()
    private val deathHerbivoresIndices = mutableListOf<Int>()

    private val breedingIndicesPredator = mutableListOf<Int>()
    private val breedingIndicesHerbivore = mutableListOf<Int>()

    private var time = System.currentTimeMillis()


    fun fillLists(predatorsCount : Int, herbivoresCount : Int, plantsCount : Int) {
        for (i in 0 until predatorsCount)
            predatorsList.add(
                PredatorV(
                    Point((0..99).random().toFloat(), (0..99).random().toFloat()),
                    (100..200).random().toFloat() / 10,
                    (50..60).random().toFloat() / 10,
                    (5..30).random().toFloat() / 20,
                    0F,
                    2F
                )
            )
        for (i in 0 until herbivoresCount) {
            val rnd01 = (0..1).random()
            val afraidOfPredator = rnd01 == 1
            herbivoresList.add(
                HerbivoreV(
                    Point((0..99).random().toFloat(), (0..99).random().toFloat()),
                    (100..200).random().toFloat() / 10,
                    (50..60).random().toFloat() / 10,
                    (5..30).random().toFloat() / 20,
                    0F,
                    2F,
                    afraidOfPredator
                )
            )
        }
        for (i in 0 until plantsCount)
            plantsList.add(
                PlantV(
                    Point((0..99).random().toFloat(), (0..99).random().toFloat()),
                    (5..30).random().toFloat() / 20,
                    (5..30).random().toFloat() / 10
                )
            )
    }


    fun doFrame(tickLength : Float) {
        Handler(Looper.getMainLooper()).postDelayed({
            val deltaTime = System.currentTimeMillis() - time

            if (deltaTime > 500) {
                time = System.currentTimeMillis()

                deathPlantsIndices.sortDescending()
                deathHerbivoresIndices.sortDescending()

                for (i in deathPlantsIndices)
                    plantsList.removeAt(i)
                for (i in deathHerbivoresIndices)
                    herbivoresList.removeAt(i)

                for (i in predatorsList.indices)
                    if (predatorsList[i].currentPoints >= predatorsList[i].pointsForBreeding) {
                        predatorsList[i].currentPoints -= predatorsList[i].pointsForBreeding
                        breedingIndicesPredator += i
                    }
                for (i in herbivoresList.indices)
                    if (herbivoresList[i].currentPoints >= herbivoresList[i].pointsForBreeding) {
                        herbivoresList[i].currentPoints -= herbivoresList[i].pointsForBreeding
                        breedingIndicesHerbivore += i
                    }

                for (i in breedingIndicesPredator)
                    predatorsList += PredatorV(
                        Point(
                            (0..99).random().toFloat(),
                            (0..99).random().toFloat()
                        ), 3F, 1F, 1F, 0F, 2F
                    )
                for (i in breedingIndicesHerbivore)
                    herbivoresList += HerbivoreV(
                        Point(
                            (0..99).random().toFloat(),
                            (0..99).random().toFloat()
                        ), 3F, 1F, 1F, 0F, 2F, true
                    )

                deathHerbivoresIndices.clear()
                deathPlantsIndices.clear()
                breedingIndicesPredator.clear()
                breedingIndicesHerbivore.clear()

                for (herbivore in herbivoresList) {
                    val index = herbivore.setDirection(herbivoresList, predatorsList, plantsList)
                    if (index != -1)
                        deathPlantsIndices.add(index)
                }
                for (predator in predatorsList) {
                    val index = predator.setDirection(herbivoresList, predatorsList, plantsList)
                    if (index != -1)
                        deathHerbivoresIndices.add(index)
                }

                for (herbivore in herbivoresList)
                    herbivore.rollBack()
                for (predator in predatorsList)
                    predator.rollBack()
            }
            else {
                for (herbivore in herbivoresList)
                    herbivore.move(deltaTime, 500)
                for (predator in predatorsList)
                    predator.move(deltaTime, 500)
            }

            fieldView.setListsToDraw(predatorsList, herbivoresList, plantsList)
            fieldView.invalidate()
            doFrame(tickLength)
        }, (tickLength).toLong())
    }



}