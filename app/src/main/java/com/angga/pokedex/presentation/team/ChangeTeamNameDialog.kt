package com.angga.pokedex.presentation.team

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.angga.pokedex.presentation.components.PokemonText
import com.angga.pokedex.presentation.ui.theme.Archive
import com.angga.pokedex.presentation.ui.theme.TeamBanner

@Composable
fun ChangeTeamNameDialog(
    onDismiss : () -> Unit,
    onButtonClick : () -> Unit,
    dialogTitle : String,
    state : TextFieldState,
    error: String? = null
) {
    Dialog(
        onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dialogTitle,
                fontFamily = Archive
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = TeamBanner,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                state = state,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                lineLimits = TextFieldLineLimits.SingleLine,//determine single or multi line
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground), //cursor color
            )
            
            Spacer(modifier = Modifier.height(4.dp))

            if (error != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onButtonClick() }
            ) {
                PokemonText(
                    color = Color.White,
                    text = "Update My Team Name"
                )
            }
        }
    }
}