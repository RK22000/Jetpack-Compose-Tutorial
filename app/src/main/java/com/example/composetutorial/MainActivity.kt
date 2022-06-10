package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.primarySurface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.composetutorial.ui.theme.SampleData


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Text("Hello World!")
            /*ComposeTutorialTheme {
                MessageCard(Message("Rahul", "Hello there!"))
            }*/
            ComposeTutorialTheme {
                Conversation(messages = SampleData.conversationSample)
            }
        }
    }

    data class Message(val author: String, val body: String)

    //Display message content and it's author
    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.kandekarrahul_square),
                contentDescription = "Contact user profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // getter and setter of isExpanded is provided BY remember
            var isExpanded by remember { //remember gives the getter setter to isExpanded, after adding its own trackers on them
                mutableStateOf(false) // Creates the getter and setter that will be given to var isExpanded
            }
            
            val surfaceColor by animateColorAsState(
                targetValue = if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            )
            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.size(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier.animateContentSize().padding(1.dp)
                ) {
                    Text(
                        text = msg.body,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )

                }

            }
        }

    }

    @Preview(
        name = "Light_Mode",
        showBackground = true
    )
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true
    )
    @Composable
    fun PreviewMessageCard() {
        ComposeTutorialTheme {
            MessageCard(
                msg = Message("Colleague", "Hey,\nTake a look at this")
            )
        }
    }
    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn{
            items(messages) { message ->
                MessageCard(msg = message)
            }
        }
    }

    @Preview(name = "Conversation")
    @Composable
    fun PreviewConversation() {
        ComposeTutorialTheme {
            Conversation(messages = SampleData.conversationSample)
        }
    }

}