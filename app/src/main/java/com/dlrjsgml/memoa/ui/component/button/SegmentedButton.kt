import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.theme.Gray60
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DodamSegmentedButton(
    modifier: Modifier = Modifier,
    segments: ImmutableList<DodamSegment>,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(SegmentedButtonDefaults.DefaultContainerHeight),
        color = Color.White,
        shape = SegmentedButtonDefaults.ContainerShape,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(SegmentedButtonDefaults.ContainerPadding)
        ) {
            val segmentWidth = remember { maxWidth / segments.size }
            val indicatorOffset by animateIntOffsetAsState(
                targetValue = IntOffset(
                    x = with(LocalDensity.current) { (segmentWidth * segments.indexOfFirst { it.selected }).toPx() }.toInt(),
                    y = -13
                ),
                label = "",
            )
            Column (
                modifier = Modifier
                    .offset { indicatorOffset }
                    .width(segmentWidth)
                    .background(
                        color = Color.White,
                        shape = SegmentedButtonDefaults.SegmentShape,
                    ),
            ){
                Spacer(modifier = Modifier.weight(0.4f))
                Box(modifier=Modifier.height(1.3.dp).align(Alignment.CenterHorizontally).fillMaxWidth(0.5f).background(Color(0xFF000000)))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                segments.fastForEach { segment ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .selectable(
                                selected = segment.selected,
                                onClick = segment.onClick,
                                enabled = segment.enabled,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberBounceIndication(showBackground = false)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = segment.text,
                            style = SegmentedButtonDefaults.TextStyle,
                            color = SegmentedButtonDefaults.SelectedTextColor.takeIf { segment.selected }
                                ?: SegmentedButtonDefaults.UnselectedTextColor.takeIf { segment.enabled }
                                ?: SegmentedButtonDefaults.UnselectedTextColor.copy(alpha = 0.5f),
                        )
                    }
                }
            }
        }
    }
}

@Immutable
data class DodamSegment(
    val selected: Boolean,
    val onClick: () -> Unit,
    val text: String,
    val enabled: Boolean = true,
)

private object SegmentedButtonDefaults {
    val ContainerColor @Composable get() =  Color(0xFF9B9D9F)
    val ContainerShape @Composable get() = RoundedCornerShape(12.dp)
    val ContainerPadding = 4.dp
    val DefaultContainerHeight = 48.dp

    val SegmentColor @Composable get() = Color(0xFFFF0000)
    val SegmentShape @Composable get() = RoundedCornerShape(8.dp)

    val SelectedTextColor @Composable get() = Color(0xFF0F0F10)
    val UnselectedTextColor @Composable get() = Gray60
    val TextStyle @Composable get() = caption1Regular
}