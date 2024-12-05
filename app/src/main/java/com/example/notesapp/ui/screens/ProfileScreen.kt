package com.example.notesapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notesapp.viewmodel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current // Context for displaying Toast messages

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Note") }) // Top app bar with a title
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize() // Fill the available space
                .padding(16.dp), // Add padding around the content
            verticalArrangement = Arrangement.Center // Center the content vertically
        ) {
            // Text field for entering the note title
            OutlinedTextField(
                value = name,
                onValueChange = { name = it }, // Update the title state on input
                label = { Text("Name") }, // Label for the text field
                modifier = Modifier.fillMaxWidth() // Field takes up the full width
            )
            Spacer(modifier = Modifier.height(16.dp)) // Space between fields

            // Text field for entering the note content
            OutlinedTextField(
                value = email,
                onValueChange = { email = it }, // Update the title state on input
                label = { Text("Email") }, // Label for the text field
                modifier = Modifier.fillMaxWidth() // Field takes up the full width
            )
            Spacer(modifier = Modifier.height(24.dp)) // Space before buttons

            // Row for arranging the Save and Cancel buttons horizontally
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Evenly space the buttons
            ) {
                // Save button
                Button(onClick = {
                    if (name.isNotBlank()) { // Ensure title is not blank
                        profileViewModel.setUserProfile(
                            name = name, // Trim whitespace from the title
                            email = email, // Trim whitespace from the content
                        )
                        Toast.makeText(context, "profile updated Successfully!", Toast.LENGTH_SHORT)
                            .show() // Show success message
                        navController.popBackStack() // Navigate back to the previous screen
                    } else {
                        Toast.makeText(context, "Name cannot be empty!", Toast.LENGTH_SHORT)
                            .show() // Show error message
                    }
                }) {
                    Text("Update") // Button label
                }
                // Cancel button
                Button(onClick = {
                    Toast.makeText(context, "Cancelled Adding Profile update", Toast.LENGTH_SHORT).show() // Show cancellation message
                    navController.popBackStack() // Navigate back to the previous screen
                }) {
                    Text("Cancel") // Button label
                }
            }
        }
    }

}