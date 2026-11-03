import { useState } from 'react'
import { AuthContext } from './authContext'
import { apiFetch, getAuth, setAuth, clearAuth } from '../api/client'

export default function AuthProvider({ children }) {
    const [auth, setAuthState] = useState(() => getAuth())

    async function login(username, password) {
        const data = await apiFetch('/api/auth/login', {
            method: 'POST',
            body: { username, password },
            auth: false,
        })
        setAuth(data)
        setAuthState(data)
        return data
    }

    function logout() {
        clearAuth()
        setAuthState(null)
    }

    return (
        <AuthContext.Provider value={{ auth, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}