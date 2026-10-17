package com.nikitadevktad.assemblyyao.ui.memos

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.nikitadevktad.assemblyyao.BuildConfig
import com.nikitadevktad.assemblyyao.R
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private data class MemoDocument(
    @RawRes val resource: Int,
    @StringRes val language: Int,
    val fileName: String,
)

private val memoDocuments = listOf(
    MemoDocument(R.raw.russia, R.string.memo_language_russian, "russian.pdf"),
    MemoDocument(R.raw.english, R.string.memo_language_english, "english.pdf"),
    MemoDocument(R.raw.armenian, R.string.memo_language_armenian, "armenian.pdf"),
    MemoDocument(R.raw.azerbaijan, R.string.memo_language_azerbaijani, "azerbaijani.pdf"),
    MemoDocument(R.raw.kyrgyz, R.string.memo_language_kyrgyz, "kyrgyz.pdf"),
    MemoDocument(R.raw.tajik, R.string.memo_language_tajik, "tajik.pdf"),
    MemoDocument(R.raw.uzbek, R.string.memo_language_uzbek, "uzbek.pdf"),
)

@Composable
fun HomeMemosSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("home_memos")
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(R.string.foreign_citizen_memos),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        ForeignCitizenMemosContent()
    }
}

@Composable
fun ForeignCitizenMemosContent(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(R.string.foreign_citizen_memos_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        memoDocuments.forEach { memo ->
            MemoDocumentRow(
                language = stringResource(memo.language),
                onClick = {
                    coroutineScope.launch {
                        openMemoDocument(context, memo)
                    }
                },
            )
        }
    }
}

@Composable
private fun MemoDocumentRow(
    language: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.document_search),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = language,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = stringResource(R.string.open_pdf),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Icon(
                painter = painterResource(R.drawable.arrow_forward),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

private suspend fun openMemoDocument(
    context: Context,
    memo: MemoDocument,
) {
    val pdfFile = runCatching {
        withContext(Dispatchers.IO) {
            val pdfDirectory = File(context.cacheDir, PDF_CACHE_DIRECTORY).apply { mkdirs() }
            val versionedFileName = memo.fileName.removeSuffix(".pdf") +
                "-${BuildConfig.VERSION_CODE}.pdf"
            val destination = File(pdfDirectory, versionedFileName)

            if (!destination.exists() || destination.length() == 0L) {
                context.resources.openRawResource(memo.resource).use { input ->
                    destination.outputStream().use(input::copyTo)
                }
            }
            destination
        }
    }.getOrElse {
        Toast.makeText(context, R.string.pdf_prepare_error, Toast.LENGTH_LONG).show()
        return
    }

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        pdfFile,
    )
    val viewIntent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, PDF_MIME_TYPE)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(viewIntent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, R.string.pdf_viewer_not_found, Toast.LENGTH_LONG).show()
    }
}

private const val PDF_CACHE_DIRECTORY = "pdfs"
private const val PDF_MIME_TYPE = "application/pdf"
