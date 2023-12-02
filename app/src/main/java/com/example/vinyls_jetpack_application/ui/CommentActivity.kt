package com.example.vinyls_jetpack_application.ui

import CommentAdapter
import CommentViewModel
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.models.Comment

class CommentActivity : AppCompatActivity() {

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentViewModel: CommentViewModel
    private val commentsList: MutableList<Comment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        commentAdapter = CommentAdapter(commentsList)

        val commentsRecyclerView: RecyclerView = findViewById(R.id.commentsRecyclerView)
        commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        commentsRecyclerView.adapter = commentAdapter

        // Obtener el albumId desde el Intent
        val albumId = intent.getIntExtra("ALBUM_ID", 0)

        commentViewModel.getCommentsForAlbum(albumId,
            { comments ->
                commentsList.addAll(comments)
                commentAdapter.notifyDataSetChanged()
            },
            { error -> /* Manejar error */ }
        )

        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {

            val commentDescriptionEditText: EditText = findViewById(R.id.commentDescriptionEditText)
            val commentRatingEditText: EditText = findViewById(R.id.commentRatingEditText)
            val commentCollectorIdEditText: EditText = findViewById(R.id.commentCollectorIdEditText)

            val commentDescription = commentDescriptionEditText.text.toString()
            val commentRating = commentRatingEditText.text.toString().toInt()
            val collectorId = commentCollectorIdEditText.text.toString()

            // Crear un nuevo comentario utilizando albumId
            val newComment = Comment(commentDescription, commentRating, collectorId)

            commentViewModel.saveComment(albumId, newComment,
                {
                    commentViewModel.getCommentsForAlbum(albumId,
                        { comments ->
                            commentsList.clear()
                            commentsList.addAll(comments)
                            commentAdapter.notifyDataSetChanged()
                        },
                        { error -> /* Manejar error */ }
                    )
                },
                { error -> /* Manejar error */ }
            )
        }
    }
}
