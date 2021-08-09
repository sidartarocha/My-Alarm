package com.sidarta.myalarme

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.sidarta.myalarme.dao.RoomManager
import com.sidarta.myalarme.data.Alarme
import com.sidarta.myalarme.databinding.FragmentTimerBinding
import com.sidarta.myalarme.helper.AlarmeHelper
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var calendarGet = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textTimeAlarm.editText?.setOnClickListener{
            gettimePicker()
        }

        binding.btnCadastrar.setOnClickListener {

            if (binding.textTimeAlarm.editText?.text.isNullOrEmpty() or binding.textTitleAlarm.editText?.text.isNullOrEmpty()){
                Toast.makeText(context, "Necessario Preenchimento do campos", Toast.LENGTH_SHORT).show()

            }else{
                var alarme = Alarme(
                    id = 0,
                    binding.textTitleAlarm.editText?.text.toString(),
                    binding.checkBoxRepetir.isChecked,
                    calendarGet.time.time
                )

                alarme = criarAlarme(alarme,  view)
                Toast.makeText(context, "Alarme Cadastrardo ${alarme.nomeAlarme}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }

        }
    }

    private fun criarAlarme(alarme: Alarme, context: View): Alarme {
       val db = view?.let { RoomManager.getInstance(it.context) }
        alarme.id = db?.getAlarme()?.insertAlarme(alarme)?.toInt()!!
        criaEvento(alarme)
    return alarme
    }

    private fun criaEvento(alarme: Alarme) {
        AlarmeHelper.scheduleRTC(
            requireContext(),
            AlarmeHelper.getAlarmManager(requireContext()),
            alarme
        )
    }


    private fun gettimePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val mHora = calendar.get(Calendar.HOUR_OF_DAY)
        val mMinuto = calendar.get(Calendar.MINUTE)
        val sdf = SimpleDateFormat("HH:mm")
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendarGet.apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                }
                binding.textTimeAlarm.editText?.setText(sdf.format(calendarGet.time))
            },
            mHora,
            mMinuto,
            true
        )
        timePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}