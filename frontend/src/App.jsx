import { Navigate, Route, Routes } from 'react-router'
import ProtectedRoute from './auth/ProtectedRoute'
import Layout from './components/Layout'
import HomeRedirect from './components/HomeRedirect'
import LoginPage from './pages/LoginPage'
import AdminUsersPage from './pages/admin/AdminUsersPage'
import AdminDevicesPage from './pages/admin/AdminDevicesPage'
import MyDevicesPage from './pages/client/MyDevicesPage'

export default function App() {
  return (
      <Routes>
        <Route path="/login" element={<LoginPage />} />

        <Route element={<ProtectedRoute><Layout /></ProtectedRoute>}>
          <Route path="/" element={<HomeRedirect />} />
          <Route path="/admin/users"
                 element={<ProtectedRoute role="ADMIN"><AdminUsersPage /></ProtectedRoute>} />
          <Route path="/admin/devices"
                 element={<ProtectedRoute role="ADMIN"><AdminDevicesPage /></ProtectedRoute>} />
          <Route path="/my-devices"
                 element={<ProtectedRoute role="CLIENT"><MyDevicesPage /></ProtectedRoute>} />
        </Route>

        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
  )
}