package com.el3asas.regym.helpers

class colSo3rat {
    fun setBodyStatusForMens(i: Int, weight: Int, height: Int, age: Int): Int {
        var BB = 0
        val BMR: Int
        BMR = (88.362 + 13.397 * weight + 4.799 * height - 5.677 * age).toInt()
        when (i) {
            1 -> BB = (1.2 * BMR).toInt()
            2 -> BB = (1.375 * BMR).toInt()
            3 -> BB = (1.5 * BMR).toInt()
        }
        return BB
    }

    fun setBodyStatusForWomans(i: Int, weight: Int, height: Int, age: Int): Int {
        var BB = 0
        val BMR: Int
        BMR = (447.593 + 9.247 * weight + 3.098 * height - 4.330 * age).toInt()
        when (i) {
            1 -> BB = (1.2 * BMR).toInt()
            2 -> BB = (1.375 * BMR).toInt()
            3 -> BB = (1.5 * BMR).toInt()
        }
        return BB
    }
}