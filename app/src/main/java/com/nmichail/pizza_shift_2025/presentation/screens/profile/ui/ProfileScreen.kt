package com.nmichail.pizza_shift_2025.presentation.screens.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.data.dto.UserDto
import com.nmichail.pizza_shift_2025.presentation.screens.profile.presentation.ProfileUiState
import com.nmichail.pizza_shift_2025.presentation.screens.profile.presentation.ProfileViewModel
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        when (uiState) {
            is ProfileUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ProfileUiState.Error -> {
                Text(
                    text = (uiState as ProfileUiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ProfileUiState.View, is ProfileUiState.Edit -> {
                val isEdit = uiState is ProfileUiState.Edit
                val user = when (uiState) {
                    is ProfileUiState.View -> (uiState as ProfileUiState.View).user
                    is ProfileUiState.Edit -> (uiState as ProfileUiState.Edit).user
                    else -> UserDto("", "", "", "", "", "")
                }
                var firstname by remember { mutableStateOf(user.firstname ?: "") }
                var lastname by remember { mutableStateOf(user.lastname ?: "") }
                var middlename by remember { mutableStateOf(user.middlename ?: "") }
                var phone by remember { mutableStateOf(user.phone ?: "") }
                var email by remember { mutableStateOf(user.email ?: "") }
                var city by remember { mutableStateOf(user.city ?: "") }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Профиль",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    ProfileField(
                        label = "Имя*",
                        value = firstname,
                        enabled = isEdit,
                        onValueChange = { firstname = it }
                    )
                    ProfileField(
                        label = "Фамилия*",
                        value = lastname,
                        enabled = isEdit,
                        onValueChange = { lastname = it }
                    )
                    ProfileField(
                        label = "Отчество*",
                        value = middlename,
                        enabled = isEdit,
                        onValueChange = { middlename = it }
                    )
                    ProfileField(
                        label = "Номер телефона*",
                        value = phone,
                        enabled = false,
                        onValueChange = { }
                    )
                    ProfileField(
                        label = "Email",
                        value = email,
                        enabled = isEdit,
                        onValueChange = { email = it }
                    )
                    ProfileField(
                        label = "Город",
                        value = city,
                        enabled = isEdit,
                        onValueChange = { city = it }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (isEdit) {
                        Row(Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    viewModel.updateProfile(
                                        UserDto(
                                            phone = phone,
                                            firstname = firstname,
                                            middlename = middlename,
                                            lastname = lastname,
                                            email = email,
                                            city = city
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(containerColor = OrangePizza)
                            ) {
                                Text("Сохранить", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedButton(
                                onClick = { viewModel.cancelEdit() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text("Отмена", fontSize = 18.sp, color = Color.Black)
                            }
                        }
                    } else {
                        OutlinedButton(
                            onClick = { viewModel.startEdit() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(50),
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                        ) {
                            Text("Обновить данные", fontSize = 18.sp, color = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                    ) {
                        Text("Выйти", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }
        if (showLogoutDialog) {
            LogoutDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = {
                    showLogoutDialog = false
                    viewModel.logout(onLogout)
                }
            )
        }
    }
}
