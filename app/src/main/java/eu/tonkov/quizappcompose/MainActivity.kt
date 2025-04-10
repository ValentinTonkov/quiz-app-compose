package eu.tonkov.quizappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tonkov.quizappcompose.ui.theme.QuizAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppComposeTheme {
            }
        }

    }
}

fun generateQuestions(): List<Question> {
    return listOf(
        Question(
            "What is the capital of France?",
            listOf("Paris", "London", "Berlin", "Madrid"),
            0
        ),
        Question("What is the capital of Bulgaria?",
            listOf("Sofia", "Plovdiv", "Varna", "Burgas"),
            0
        ),
        Question("What is the capital of Germany?",
            listOf("Munich", "Berlin", "Frankfurt", "Hamburg"),
            1),
        Question("What is the capital of Italy?",
            listOf("Rome", "Milan", "Naples", "Turin"),
            0)
    )
}

@Composable
fun QuestionCard(question: Question) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Column() {
            Text(
                modifier = Modifier.padding(8.dp),
                text = question.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
                )
            question.options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = false, onClick = null)
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun QuestionCardPreview() {
    val question = Question(
        "What is the capital of France?",
        listOf("Paris", "London", "Berlin", "Madrid"),
        0)
    Column {
        Spacer(modifier = Modifier.padding(60.dp))
        QuestionCard(question = question)
    }

}

