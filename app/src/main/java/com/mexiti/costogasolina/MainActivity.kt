package com.mexiti.costogasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray //Cambio fondo a gris
                ) {
                    CostGasLayout("Android")
                }
            }
        }
    }
}

@Composable
fun CostGasLayout(name: String) {
    var precioLitroEntrada by remember{
        mutableStateOf("")
    }
    var cantLitroEntrada by remember{
        mutableStateOf("")
    }
    var propinaEntrada by remember{
        mutableStateOf("")
    }
    val precioLitro = precioLitroEntrada.toDoubleOrNull() ?: 0.0
    val cantLitro = cantLitroEntrada.toDoubleOrNull() ?: 0.0
    val propina = propinaEntrada.toDoubleOrNull() ?: 0.0
    val monto = calcularMonto(precioLitro, cantLitro, propina)


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.calcular_monto),

            )
       EditNumberField(
           label = R.string.ingresa_gasolina,
           leadingIcon = R.drawable.money_gas ,
           keyboardsOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Number,
               imeAction = ImeAction.Next
           ),
           value = precioLitroEntrada,
           onValueChanged = {
               precioLitroEntrada = it
           }
       )
        EditNumberField(
            label = R.string.litros,
            leadingIcon = R.drawable.gas_24,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = cantLitroEntrada,
            onValueChanged = {
                cantLitroEntrada = it
            }
        )
        EditNumberField(
            label = R.string.propina,
            leadingIcon = R.drawable.propin_24,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = propinaEntrada,
            onValueChanged = {
                propinaEntrada = it
            }
        )
        Switch(
            checked = true,
            onCheckedChange =  {propinaEntrada = ""}
        )
        Text(
            text = stringResource(R.string.monto_total, monto   )
        )

    }

}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions:KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label))  },
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon) , contentDescription = null) },
        keyboardOptions = keyboardsOptions,
        modifier = modifier,
        onValueChange = onValueChanged
    )

}

@Preview(showBackground = true)
@Composable
fun CostGasLayoutPreview() {
    CostoGasolinaTheme {
        CostGasLayout("Android")
    }
}
private fun calcularMonto(precio: Double, cantLitros: Double, cantPropina: Double):String{
    val monto= precio * cantLitros+cantPropina
    return NumberFormat.getCurrencyInstance().format(monto)
}