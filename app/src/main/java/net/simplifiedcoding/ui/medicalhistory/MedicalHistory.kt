package net.simplifiedcoding.ui.medicalhistory



import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text


import android.annotation.SuppressLint
import android.widget.Toast
import android.content.Context
import android.content.Intent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import net.simplifiedcoding.Patient
import net.simplifiedcoding.navigation.ROUTE_HOME


import net.simplifiedcoding.ui.auth.AuthViewModel
import kotlin.coroutines.jvm.internal.CompletedContinuation.context


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
 fun MedicalhistoryScreen(viewModel: AuthViewModel?, navController: NavHostController) {

    TopAppBar(title = { androidx.compose.material.Text(text = "Contact Us") },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(ROUTE_HOME)
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack , contentDescription = "arrowback")
            }
        },
        actions = {
            IconButton(onClick = {
                val shareIntent= Intent(Intent.ACTION_SEND)
                shareIntent.type="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this is a cool content")
                context.startActivity(Intent.createChooser(shareIntent, "Share"))


            }) {
                Icon(imageVector = Icons.Filled.Call, contentDescription = "share")
            }
            IconButton(onClick = {
                context.startActivity(Intent(context, Homes::class.java))
            }) {
                Icon(imageVector = Icons.Filled.LocationOn , contentDescription = "Location")
            }
        },
        backgroundColor = Color.Blue)
    Spacer(modifier = Modifier.height(20.dp))



    Surface(
        // on below line we are specifying modifier and color for our app
        modifier = Modifier.fillMaxSize(),
    ) {
        // on the below line we are specifying
        // the theme as the scaffold.
        Scaffold(
            // in scaffold we are specifying the top bar.
            topBar = {
                // inside top bar we are specifying
                // background color.
                SmallTopAppBar(
                    // along with that we are
                    // specifying title for our top bar.
                    title = {
                        // in the top bar we are specifying
                        // tile as a text
                        Text(
                            // on below line we are specifying
                            // text to display in top app bar
                            text = "Displaying Patients List",
                            // on below line we are specifying
                            // modifier to fill max width
                            modifier = Modifier.fillMaxWidth(),
                            // on below line we are specifying
                            // text alignment
                            textAlign = TextAlign.Center,
                            // on below line we are specifying
                            // color for our text.
                            color = Color.White
                        )
                    })
            }) {


            //Logic for retrieving data

            //creating a variable to save all our courses
            var patientlist = mutableStateListOf<Patient?>()

            //create a variable for firestore database data
            var db: FirebaseFirestore = FirebaseFirestore.getInstance()

            //get data from our database
            db.collection("Patients").get()

                //try and catch from firebase
                .addOnSuccessListener { queryDocumentSnapshot ->
                    if (!queryDocumentSnapshot.isEmpty) {
                        //perform a loop
                        var list = queryDocumentSnapshot.documents
                        for (d in list) {

                            var c: Patient? = d.toObject(Patient::class.java)
                            patientlist.add(c)

                        }
                    } else {

                        // Toast.makeText(context, "Failed to fetch Data", Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener {
                    //Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show()
                }


            //adding our user interface
            firebaseUI(LocalContext.current, patientlist)
        }
    }
}

@Composable
fun Text(text: String) {

}

@Composable
fun Text(text: String) {

}

@Composable
fun TopAppBar(title: () -> Unit, navigationIcon: () -> Unit, actions: () -> Unit, backgroundColor: Color) {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <Patient> firebaseUI(context: Context, patientList: SnapshotStateList<Patient?>) {

    // on below line creating a column
    // to display our retrieved list.
    Column(
        // adding modifier for our column
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        // on below line adding vertical and
        // horizontal alignment for column.
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are
        // calling lazy column
        // for displaying listview.
        LazyColumn {
            // on below line we are setting data
            // for each item of our listview.
            itemsIndexed(patientList) { index, item ->
                // on below line we are creating
                // a card for our list view item.
                Card(
                    onClick = {
                        // inside on click we are
                        // displaying the toast message.
                        Toast.makeText(
                            context,
                            patientList[index]?.patientName + " selected..",
                            Toast.LENGTH_SHORT
                        ).show()
                    },

                    // on below line we are adding
                    // padding from our all sides.
                    modifier = Modifier.padding(8.dp),

                    // on below line we are adding
                    // elevation for the card.

                ) {
                    // on below line we are creating
                    // a row for our list view item.
                    Column(
                        // for our row we are adding modifier
                        // to set padding from all sides.
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        // on below line inside row we are adding spacer
                        Spacer(modifier = Modifier.width(5.dp))
                        // on below line we are displaying course name.
                        patientList[index]?.PatientIllness?.let {
                            Text(
                                // inside the text on below line we are
                                // setting text as the language name
                                // from our modal class.
                                text = it,

                                // on below line we are adding padding
                                // for our text from all sides.
                                modifier = Modifier.padding(4.dp),

                                // on below line we are adding
                                // color for our text

                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 20.sp, fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        // adding spacer on below line.
                        Spacer(modifier = Modifier.height(5.dp))

                        // on below line displaying text for course duration
                        patientList[index]?.PatientCondition?.let {
                            Text(
                                // inside the text on below line we are
                                // setting text as the language name
                                // from our modal class.
                                text = it,

                                // on below line we are adding padding
                                // for our text from all sides.
                                modifier = Modifier.padding(4.dp),

                                // on below line we are
                                // adding color for our text
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }
                }
            }

        }
    }
}



