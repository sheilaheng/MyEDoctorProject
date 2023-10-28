package net.simplifiedcoding.ui.appointments



import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import net.simplifiedcoding.Patient
import net.simplifiedcoding.navigation.ROUTE_HOME
import net.simplifiedcoding.navigation.ROUTE_MEDICALHISTORY
import net.simplifiedcoding.ui.auth.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(viewModel: AuthViewModel?, navController: NavController){

    Surface(
        // on below line we are specifying modifier and color for our app
        modifier = Modifier.fillMaxSize(),
    ) {
        // on the below line we are specifying the theme as the scaffold.
        Scaffold(
            // in scaffold we are specifying the top bar.
            topBar = {
                // inside top bar we are specifying background color.
                SmallTopAppBar(
                    // along with that we are specifying
                    // title for our top bar.
                    title = {
                        // in the top bar we are
                        // specifying tile as a text
                        Text(

                            // on below line we are specifying
                            // text to display in top app bar
                            text = "ADD YOUR SYMPTOMS",
                            // on below line we are specifying
                            // modifier to fill max width
                            modifier = Modifier.fillMaxWidth(),
                            // on below line we are
                            // specifying text alignment
                            textAlign = TextAlign.Center,
                            // on below line we are specifying
                            // color for our text.
                            color = Color.White
                        )
                    })
            }) {
            // on below line we are calling
            // method to display UI
            FirebaseUI(LocalContext.current, navController)
        }
    }


}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseUI(context: Context, navController: NavController) {

    // on below line creating variable for course name,
    // course duration and course description.
    val patientName = remember {
        mutableStateOf("")
    }

    val patientIllness = remember {
        mutableStateOf("")
    }

    val patientCondition = remember {
        mutableStateOf("")
    }


    // on below line creating a column
    // to display our retrieved image view.
    Column(


        // adding modifier for our column
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        // on below line adding vertical and
        // horizontal alignment for column.
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {


        TextField(
            // on below line we are specifying
            // value for our course name text field.
            value = patientName.value,

            // on below line we are adding on
            // value change for text field.
            onValueChange = { patientName.value = it },

            // on below line we are adding place holder
            // as text as "Enter your course name"
            placeholder = { Text(text = "Enter your name") },

            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            // on below line we are adding
            // single line to it.
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            // on below line we are specifying
            // value for our course duration text field.
            value = patientIllness.value,

            // on below line we are adding on
            // value change for text field.
            onValueChange = { patientIllness.value = it },

            // on below line we are adding place holder
            // as text as "Enter your course duration"
            placeholder = { Text(text = "Enter your Illness") },

            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            // on below line we are adding
            // single line to it.
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            // on below line we are specifying
            // value for our course description text field.
            value = patientCondition.value,

            // on below line we are adding on
            // value change for text field.
            onValueChange = { patientCondition.value = it },

            // on below line we are adding place holder
            // as text as "Enter your course description"
            placeholder = { Text(text = "Enter your Current Illness State") },

            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            // on below line we are adding
            // single line to it.
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))
        // on below line creating button to
        // add data to firebase firestore database.
        Button(
            onClick = {
                // on below line we are validating user input parameters.
                if (TextUtils.isEmpty(patientName.value.toString())) {
                    Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(patientIllness.value.toString())) {
                    Toast.makeText(context, "Please enter your Illness", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(patientCondition.value.toString())) {
                    Toast.makeText(context, "Please enter your illness state", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // on below line adding data to
                    // firebase fire-store database.
                    addDataToFirebase(
                        patientName.value,
                        patientIllness.value,
                        patientCondition.value, context
                    )
                }
            },
            // on below line we are
            // adding modifier to our button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // on below line we are adding text for our button
            Text(text = "Add Data", modifier = Modifier.padding(8.dp))
        }



        //use text view as a button



        Button(onClick = {

            navController.navigate(ROUTE_MEDICALHISTORY)
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            
            
            ) {
            Text(text = "MedicalHistory" )
            
        }



        Button(onClick = {

            navController.navigate(ROUTE_HOME)
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)


        ) {
            Text(text = "Home" )

        }





    }
}



fun addDataToFirebase(

    patientName: String,
    patientIllness: String,
    patientCondition: String,
    context: Context
) {

    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    //creating a collection reference for our Firebase Firestore database.
    val dbPatients: CollectionReference = db.collection("Patients")
    //adding our data to our courses object class.
    val patients = Patient(patientName, patientIllness, patientCondition)

    //below method is use to add data to Firebase Firestore.
    dbPatients.add(patients).addOnSuccessListener {
        // after the data addition is successful
        // we are displaying a success toast message.
        Toast.makeText(
            context,
            "Your Record has been uploaded Successfuly",
            Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        // this method is called when the data addition process is failed.
        // displaying a toast message when data addition is failed.
        Toast.makeText(context, "Failed to add record Please Try Again\n$e", Toast.LENGTH_SHORT).show()
    }
    
    
    
    

}
