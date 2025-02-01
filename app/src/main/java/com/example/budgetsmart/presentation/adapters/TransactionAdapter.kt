import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetsmart.R
import com.example.budgetsmart.databinding.ItemTransactionBinding
import com.example.budgetsmart.domain.model.Transaction
import com.example.budgetsmart.domain.model.enums.TransactionType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions: List<Transaction> = emptyList()

    fun updateTransactions(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            // Set category
            binding.iTransactionLBLCategory.text = transaction.category.displayName

            // Format and set amount with color
            binding.iTransactionLBLAmount.apply {
                text = formatAmount(transaction.amount)
                setTextColor(
                    ContextCompat.getColor(context,
                    when (transaction.type) {
                        TransactionType.INCOME -> R.color.green
                        TransactionType.EXPENSE -> R.color.red
                    }
                ))
            }

            // Set date
            binding.iTransactionLBLDate.text = formatDate(transaction.date)

            // Set transaction type image
            binding.iTransactionIMGAction.setImageResource(
                when (transaction.type) {
                    TransactionType.INCOME -> R.drawable.ic_plus
                    TransactionType.EXPENSE -> R.drawable.ic_minus
                }
            )
        }

        private fun formatAmount(amount: Double): String {
            return NumberFormat.getCurrencyInstance(Locale.US).format(amount)
        }

        private fun formatDate(date: LocalDateTime): String {
            return DateTimeFormatter
                .ofPattern("MMM dd, yyyy")
                .format(date)
        }
    }
}