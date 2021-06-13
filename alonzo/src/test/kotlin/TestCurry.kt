import app.khadga.alonzo.annotations.Curry

class TestCurry {
}

@Curry
fun foo(a: Int, b: String): Int {
    println(b)
    return a * 2
}