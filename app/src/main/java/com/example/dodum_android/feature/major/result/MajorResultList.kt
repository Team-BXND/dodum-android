package com.example.dodum_android.feature.major.result

import androidx.annotation.DrawableRes
import com.example.dodum_android.R

data class MajorInfo(
    val description: String,
    val skills: List<String>,
    @DrawableRes val imageRes: Int?
)

val majors: Map<String, MajorInfo> = mapOf(
    "web" to MajorInfo(
        description = "웹 프론트엔드 개발자는 사용자가 직접 보고 상호작용하는 웹사이트의 화면과 인터페이스를 개발하는 전문가입니다. HTML, CSS, JavaScript를 기반으로 React, Vue.js 등의 프레임워크를 활용하여 사용자 친화적이고 반응형인 웹 애플리케이션을 구현합니다. 디자이너가 만든 UI/UX 디자인을 실제 동작하는 코드로 변환하며, 웹사이트의 성능 최적화와 크로스 브라우저 호환성을 보장하는 역할을 담당합니다.",
        skills = listOf("HTML", "CSS", "JavaScript", "React", "TypeScript"),
        imageRes = R.drawable.web
    ),
    "server" to MajorInfo(
        description = "서버 개발자는 웹사이트, 애플리케이션 또는 기타 소프트웨어 시스템의 서버 측을 개발하고 유지 관리하는 역할을 합니다.",
        skills = listOf("Node.js", "Express", "Python", "Django", "Java", "Spring"),
        imageRes = R.drawable.server
    ),
    "ios" to MajorInfo(
        description = "iOS 개발자는 iPhone, iPad 및 기타 Apple 기기용 애플리케이션을 개발하는 역할을 합니다.",
        skills = listOf("Swift", "Objective-C", "Xcode", "UIKit", "Core Data"),
        imageRes = R.drawable.ios
    ),
    "android" to MajorInfo(
        description = "안드로이드 개발자는 Android 운영 체제를 위한 애플리케이션을 개발하는 역할을 합니다.",
        skills = listOf("Java", "Kotlin", "Android Studio", "XML", "Firebase"),
        imageRes = R.drawable.android
    )
)
