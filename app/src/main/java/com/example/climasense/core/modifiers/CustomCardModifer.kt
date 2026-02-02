package com.example.climasense.core.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.customCard(
    borderRadius: Double? = null,
    paddingValues: PaddingValues? = null,
    backgroundColor: Color? = null
): Modifier {
    return this
        .shadow(
            elevation = 3.dp,
            shape = RoundedCornerShape((borderRadius ?: 20.0).dp),
            clip = false,
            spotColor = Color(0xFF5C4A3A).copy(alpha = 0.3f),
            ambientColor = Color(0xFF5C4A3A).copy(alpha = 0.3f)
        )
        .clip(RoundedCornerShape((borderRadius ?: 20.0).dp))
        .background(
            color = (backgroundColor ?: MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(
                (borderRadius ?: 20.0).dp
            )
        )
        .padding(paddingValues ?: PaddingValues(14.dp))


}