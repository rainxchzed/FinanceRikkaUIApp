package zed.rainxch.financerikkauiapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform