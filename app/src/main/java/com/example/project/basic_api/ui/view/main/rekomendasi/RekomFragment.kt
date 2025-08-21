package com.example.project.basic_api.ui.view.main.rekomendasi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.project.R
import com.example.project.databinding.FragmentRekomBinding
import org.json.JSONObject
import java.nio.charset.Charset

class RekomFragment : Fragment() {

    private var url = "http://10.0.2.2:5000/predict"
    private var _binding: FragmentRekomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRekomBinding.inflate(inflater, container, false)
        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.title = "Rekomendasi Pekerjaan"
        toolbar.setNavigationIcon(R.drawable.ic_back2)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deteksi = binding.button3
        val hasil = binding.hasil


        val sharedPreferences = requireActivity().getSharedPreferences("HasilPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val scale = arrayOf("1", "2", "3", "4", "5", "6", "7")

        val spinnerDatabase = binding.spinnerDatabaseFundamentals
        val adapterDatabase = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterDatabase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDatabase.adapter = adapterDatabase

        val spinnerComputer = binding.spinnerComputerArchitecture
        val adapterComputer = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterComputer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerComputer.adapter = adapterComputer

        val spinnerDistributed = binding.spinnerDistributedComputingSystems
        val adapterDistributed = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterDistributed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDistributed.adapter = adapterDistributed

        val spinnerCyberSecurity = binding.spinnerCyberSecurity
        val adapterCyberSecurity = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterCyberSecurity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCyberSecurity.adapter = adapterCyberSecurity

        val spinnerNetworking = binding.spinnerNetworking
        val adapterNetworking = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterNetworking.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNetworking.adapter = adapterNetworking

        val spinnerSoftwareDevelopment = binding.spinnerSoftwareDevelopment
        val adapterSoftwareDevelopment = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterSoftwareDevelopment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSoftwareDevelopment.adapter = adapterSoftwareDevelopment

        val spinnerProgrammingSkills = binding.spinnerProgrammingSkills
        val adapterProgrammingSkills = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterProgrammingSkills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProgrammingSkills.adapter = adapterProgrammingSkills

        val spinnerProjectManagement = binding.spinnerProjectManagement
        val adapterProjectManagement = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterProjectManagement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProjectManagement.adapter = adapterProjectManagement

        val spinnerForensics = binding.spinnerComputerForensicsFundamental
        val adapterForensics = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterForensics.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerForensics.adapter = adapterForensics

        val spinnerTechnicalCommunication = binding.spinnerTechnicalCommunication
        val adapterTechnicalCommunication = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterTechnicalCommunication.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTechnicalCommunication.adapter = adapterTechnicalCommunication

        val spinnerMachineLearning = binding.spinnerMachineLearning
        val adapterMachineLearning = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterMachineLearning.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMachineLearning.adapter = adapterMachineLearning

        val spinnerSoftwareEngineering = binding.spinnerSoftwareEngineering
        val adapterSoftwareEngineering = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterSoftwareEngineering.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSoftwareEngineering.adapter = adapterSoftwareEngineering

        val spinnerBusinessAnalysis = binding.spinnerBusinessAnalysis
        val adapterBusinessAnalysis = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterBusinessAnalysis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBusinessAnalysis.adapter = adapterBusinessAnalysis

        val spinnerCommunicationSkills = binding.spinnerCommunicationSkills
        val adapterCommunicationSkills = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterCommunicationSkills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCommunicationSkills.adapter = adapterCommunicationSkills

        val spinnerDataScience = binding.spinnerDataScience
        val adapterDataScience = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterDataScience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDataScience.adapter = adapterDataScience

        val spinnerTroubleshooting = binding.spinnerTroubleshooting
        val adapterTroubleshooting = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterTroubleshooting.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTroubleshooting.adapter = adapterTroubleshooting

        val spinnerGraphicDesigning = binding.spinnerGraphicDesigning
        val adapterGraphicDesigning = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, scale)
        adapterGraphicDesigning.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGraphicDesigning.adapter = adapterGraphicDesigning

        deteksi.setOnClickListener {
            val jsonObject = JSONObject().apply {
                put("Database Fundamentals", spinnerDatabase.selectedItem.toString())
                put("Computer Architecture", spinnerComputer.selectedItem.toString())
                put("Distributed Computing Systems", spinnerDistributed.selectedItem.toString())
                put("Cyber Security", spinnerCyberSecurity.selectedItem.toString())
                put("Networking", spinnerNetworking.selectedItem.toString())
                put("Software Development", spinnerSoftwareDevelopment.selectedItem.toString())
                put("Programming Skills", spinnerProgrammingSkills.selectedItem.toString())
                put("Project Management", spinnerProjectManagement.selectedItem.toString())
                put("Computer Forensics Fundamentals", spinnerForensics.selectedItem.toString())
                put("Technical Communication", spinnerTechnicalCommunication.selectedItem.toString())
                put("AI ML", spinnerMachineLearning.selectedItem.toString())
                put("Software Engineering", spinnerSoftwareEngineering.selectedItem.toString())
                put("Business Analysis", spinnerBusinessAnalysis.selectedItem.toString())
                put("Communication skills", spinnerCommunicationSkills.selectedItem.toString())
                put("Data Science", spinnerDataScience.selectedItem.toString())
                put("Troubleshooting skills", spinnerTroubleshooting.selectedItem.toString())
                put("Graphics Designing", spinnerGraphicDesigning.selectedItem.toString())
            }

            val jsonObjectRequest = object : JsonObjectRequest(
                Method.POST, url, jsonObject,
                Response.Listener { response ->
                    try {
                        val data = response.getString("Prediction")

                        val resultText = when (data) {
                            "Database Administrator" -> "Database Administrator"
                            "Hardware Engineer" -> "Hardware Engineer"
                            "Application Support Engineer" -> "Application Support Engineer"
                            "Cyber Security Specialist" -> "Cyber Security Specialist"
                            "Networking Engineer" -> "Networking Engineer"
                            "Software Developer" -> "Software Developer"
                            "API Specialist" -> "API Specialist"
                            "Project Manager" -> "Project Manager"
                            "Information Security Specialist" -> "Information Security Specialist"
                            "Technical Writer" -> "Technical Writer"
                            "AI ML Specialist" -> "AI ML Specialist"
                            "Software tester" -> "Software tester"
                            "Business Analyst" -> "Business Analyst"
                            "Customer Service Executive" -> "Customer Service Executive"
                            "Data Scientist" -> "Data Scientist"
                            "Helpdesk Engineer" -> "Helpdesk Engineer"
                            "Graphics Designer" -> "Graphics Designer"
                            else -> "Tidak Ada Penempatan"
                        }

                        hasil.text = resultText
                        editor.putString("hasil", resultText)
                        editor.apply()

                        val showPopUp = HasilFragment()
                        showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("Response Error", "Error parsing response: ${e.message}")
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getBody(): ByteArray {
                    return jsonObject.toString().toByteArray(Charset.defaultCharset())
                }
            }

            val queue = Volley.newRequestQueue(requireContext())
            queue.add(jsonObjectRequest)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
