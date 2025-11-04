package com.example.utsapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.utsapp.R
import com.google.android.material.button.MaterialButton
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorFragment : Fragment() {

	private lateinit var tvResult : TextView
	private lateinit var tvExpression : TextView

	private var expression = ""        // Menampung ekspresi yang sedang diketik
	private var currentInput = ""      // Menampung angka yang sedang diketik
	private var lastResult = 0.0       // Hasil terakhir
	private var justEvaluated = false  // Apakah baru selesai menghitung?

	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?
	) : View {
		val view = inflater.inflate(R.layout.calculator_layout, container, false)

		tvResult = view.findViewById(R.id.tvResult)
		tvExpression = view.findViewById(R.id.tvExpression)

		// =====================
		// 1️⃣ Tombol Angka
		// =====================
		val numberButtons = listOf("0", "00", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".")
		for (num in numberButtons) {
			val resId = resources.getIdentifier("btn_$num", "id", requireContext().packageName)
			view.findViewById<MaterialButton?>(resId)?.setOnClickListener { numberPressed(num) }
		}

		// =====================
		// 2️⃣ Operator Dasar
		// =====================
		val ops = mapOf(
			"plus" to "+", "minus" to "-", "x" to "*", "divide" to "/", "percent" to "%"
		)
		for ((name, symbol) in ops) {
			val resId = resources.getIdentifier("btn_$name", "id", requireContext().packageName)
			view.findViewById<MaterialButton?>(resId)?.setOnClickListener { operatorPressed(symbol) }
		}

		// =====================
		// 3️⃣ Tombol Fungsi Khusus
		// =====================
		view.findViewById<MaterialButton?>(R.id.btn_clear)?.setOnClickListener { clearAll() }
		view.findViewById<MaterialButton?>(R.id.btn_equal)?.setOnClickListener { evaluateExpression() }
		view.findViewById<MaterialButton?>(R.id.btn_square)?.setOnClickListener { squarePressed() }
		view.findViewById<MaterialButton?>(R.id.btn_sqrt)?.setOnClickListener { sqrtPressed() }
		view.findViewById<MaterialButton?>(R.id.btn_pi)?.setOnClickListener { insertPi() }
		view.findViewById<MaterialButton?>(R.id.btn_factorial)
			?.setOnClickListener { factorialPressed() }
		view.findViewById<MaterialButton?>(R.id.btn_delete)?.setOnClickListener { deleteLast() }

		return view
	}

	// =====================
	// LOGIKA FUNGSI
	// =====================

	@SuppressLint("DefaultLocale")
	private fun numberPressed(value : String) {
		if (justEvaluated) {
			expression = ""
			justEvaluated = false
		}

		// Validasi input titik
		if (value == "." && currentInput.contains(".")) return

		currentInput += value
		expression += value
		tvExpression.text = expression
		tvResult.text = formatResult(currentInput.toDoubleOrNull() ?: 0.0)
	}

	private fun operatorPressed(op : String) {
		if (op == "%") {
			if (currentInput.isNotEmpty()) {
				val num = currentInput.toDoubleOrNull() ?: return
				val tokens = expression.trim().split(" ")
				val lastOperatorIndex = tokens.indexOfLast { it in listOf("+", "-", "*", "/") }

				val base = if (lastOperatorIndex != - 1) {
					tokens[lastOperatorIndex - 1].toDoubleOrNull() ?: 1.0
				} else {
					1.0
				}

				val result = base * num / 100.0
				tvResult.text = formatResult(result)
				currentInput = result.toString()

				expression = if (lastOperatorIndex != - 1) {
					tokens.take(lastOperatorIndex + 1).joinToString(" ") + " $result"
				} else {
					result.toString()
				}
				tvExpression.text = expression
			}
			return
		}

		// Validasi agar operator tidak berturut-turut
		if (expression.isNotEmpty() && ! expression.last().isOperator()) {
			expression += " $op "
			currentInput = ""
			tvExpression.text = expression
		}
	}

	private fun evaluateExpression() {
		try {
			val tokens = expression.split(" ")
			val result = calculateTokens(tokens)
			tvResult.text = formatResult(result)
			lastResult = result
			justEvaluated = true
		} catch (e : Exception) {
			tvResult.text = "Error"
		}
	}

	private fun clearAll() {
		expression = ""
		currentInput = ""
		lastResult = 0.0
		justEvaluated = false
		tvExpression.text = ""
		tvResult.text = "0"
	}

	private fun deleteLast() {
		if (expression.isNotEmpty()) {
			expression = expression.dropLast(1)
			tvExpression.text = expression

			currentInput = currentInput.dropLast(1)
			tvResult.text = currentInput
		}
	}

	private fun squarePressed() {
		if (currentInput.isNotEmpty()) {
			val num = currentInput.toDouble()
			val result = num.pow(2)
			tvResult.text = formatResult(result)
			currentInput = result.toString()
			expression = currentInput
		}
	}

	private fun sqrtPressed() {
		if (currentInput.isNotEmpty()) {
			val num = currentInput.toDouble()
			val result = sqrt(num)
			tvResult.text = formatResult(result)
			currentInput = result.toString()
			expression = currentInput
		}
	}

	private fun insertPi() {
		val value = Math.PI
		currentInput = value.toString()
		expression += value
		tvExpression.text = expression
		tvResult.text = formatResult(value)
	}

	private fun factorialPressed() {
		if (currentInput.isNotEmpty()) {
			val n = currentInput.toDouble().toInt()
			val result = factorial(n)
			tvResult.text = formatResult(result.toDouble())
			currentInput = result.toString()
			expression = currentInput
		}
	}

	// =====================
	// FUNGSI BANTUAN
	// =====================

	private fun Char.isOperator() : Boolean = this in listOf('+', '-', '*', '/', '%')

	private fun factorial(n : Int) : Long {
		if (n < 0) return 0
		var result = 1L
		for (i in 1 .. n) result *= i
		return result
	}

	private fun calculateTokens(tokens : List<String>) : Double { // Operasi sederhana
		var result = tokens[0].toDouble()
		var i = 1
		while (i < tokens.size) {
			val op = tokens[i]
			val next = tokens[i + 1].toDouble()
			result = when (op) {
				"+" -> result + next
				"-" -> result - next
				"*" -> result * next
				"/" -> if (next != 0.0) result / next else Double.NaN
				"%" -> result
				else -> result
			}
			i += 2
		}
		return result
	}

	@SuppressLint("DefaultLocale")
	private fun formatResult(value : Double) : String {
		val formatted = if (value % 1 == 0.0) {
			"%,d".format(value.toLong())  // ribuan untuk integer
		} else {
			String.format("%,.6f", value).trimEnd('0').trimEnd('.')  // ribuan + desimal rapi
		}
		return formatted
	}
}