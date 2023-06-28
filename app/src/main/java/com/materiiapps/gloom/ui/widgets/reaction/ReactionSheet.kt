package com.materiiapps.gloom.ui.widgets.reaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.gql.type.ReactionContent
import com.materiiapps.gloom.utils.Constants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ReactionSheet(
    forRelease: Boolean,
    onReactionClick: (ReactionContent) -> Unit,
    onClose: () -> Unit
) {
    val emojis = Constants.REACTION_EMOJIS.filter { (reaction, _) ->
        if (forRelease)
            !listOf(
                ReactionContent.THUMBS_DOWN,
                ReactionContent.CONFUSED,
                ReactionContent.UNKNOWN__
            ).contains(reaction)
        else
            reaction != ReactionContent.UNKNOWN__
    }

    ModalBottomSheet(
        onDismissRequest = onClose
    ) {
        FlowRow(
            maxItemsInEachRow = emojis.size / 2,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            emojis.forEach { (reaction, emoji) ->
                Text(
                    text = emoji,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .weight(1f)
                        .clickable { onReactionClick(reaction) }
                        .padding(16.dp)
                )
            }
        }
    }
}