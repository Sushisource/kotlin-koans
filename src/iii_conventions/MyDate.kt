package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        val yc = year.compareTo(other.year)
        if (yc != 0) return yc
        val mc = month.compareTo(other.month)
        if (mc != 0) return mc
        return dayOfMonth.compareTo(other.dayOfMonth)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return this.addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(rti: RepeatedTimeInterval): MyDate {
    return this.addTimeIntervals(rti.ti, rti.n)
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(other: Int) = RepeatedTimeInterval(this, other)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var curD = start
            override fun next(): MyDate {
                val t = curD
                curD = curD.nextDay()
                return t
            }

            override fun hasNext(): Boolean {
                return curD <= endInclusive
            }
        }
    }

    operator fun contains(d: MyDate): Boolean {
        return d in start..endInclusive
    }
}
