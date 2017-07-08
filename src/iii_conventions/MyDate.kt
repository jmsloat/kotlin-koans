package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        val dateVal = (year*10000) + (month*100) + dayOfMonth
        val otherVal  = (other.year*10000) + (other.month*100) + other.dayOfMonth

        return dateVal - otherVal
    }

}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate>  = DateIterator(this)

    operator fun contains(date: MyDate): Boolean {
        return date > start && date < endInclusive
    }

}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }

    override fun hasNext(): Boolean = current <= dateRange.endInclusive

}


class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)
operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

operator fun MyDate.plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)
operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) = addTimeIntervals(timeIntervals.timeInterval, timeIntervals.number)
