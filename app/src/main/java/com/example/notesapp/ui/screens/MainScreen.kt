package com.example.notesapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, noteViewModel: NoteViewModel, profileViewModel: ProfileViewModel ) {
    // Observing the notes from the ViewModel as state
    val notes by noteViewModel.notes.observeAsState(emptyList())

    val profile by profileViewModel.userProfile.observeAsState()

    // Scaffold provides the basic structure for the screen
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("NotesApp") },
                actions = {
                    IconButton(onClick = {/*edit profile screen*/
                        navController.navigate("profile")}) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "profile")
                    }
                }) // Top app bar with title
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_note") } // Navigate to the Add Note screen
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note") // Icon for the FAB
            }
        }
    ) { innerPadding ->

//name and email

        Column(modifier = Modifier.padding(innerPadding)) {
    Text(text = "${profile?.name ?: "N/A"}")
    Text(text = "${profile?.email ?: "N/A"}")
}
        Spacer(modifier = Modifier.height(16.dp))
        // LazyColumn to display a list of notes
        LazyColumn(
            modifier = Modifier.fillMaxSize(), // Fill the available space
            contentPadding = innerPadding // Add padding around the content
        ) {
            // Iterate through the list of notes and display each as a NoteItem
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onClick = {
                        navController.navigate("detail/${note.id}") // Navigate to the detail screen for the selected note
                    }
                )
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    // Card component for individual note display
    val isChecked = remember { mutableStateOf(note.isCompleted) }

    Card(
        modifier = Modifier
            .fillMaxWidth() // Card takes up the full width of the screen
            .padding(vertical = 4.dp) // Vertical spacing between cards
            .clickable(onClick = onClick), // Clickable to trigger navigation or actions
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)// Elevation for shadow effect
    ) {
        // Column to arrange the title and content of the note vertically
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title, // Display the title of the note
                    style = MaterialTheme.typography.labelSmall // Apply headline 6 typography
                )
                Spacer(modifier = Modifier.height(4.dp)) // Small space between title and content
                Text(
                    text = note.content, // Display the content of the note
                    style = MaterialTheme.typography.bodyMedium // Apply body 2 typography
                )
                Spacer(modifier = Modifier.height(4.dp))


            }
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it } // Update the state of the checkbox
            )
        }

    }
}