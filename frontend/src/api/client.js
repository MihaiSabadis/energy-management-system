const STORAGE_KEY = 'auth'

export function getAuth() {
    try {
        return JSON.parse(localStorage.getItem(STORAGE_KEY))
    } catch {
        return null
    }
}

export function setAuth(auth) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(auth))
}

export function clearAuth() {
    localStorage.removeItem(STORAGE_KEY)
}

export async function apiFetch(path, { method = 'GET', body, auth = true } = {}) {
    const headers = {}
    if (body !== undefined) headers['Content-Type'] = 'application/json'

    const stored = getAuth()
    if (auth && stored?.token) headers['Authorization'] = `Bearer ${stored.token}`

    const res = await fetch(path, {
        method,
        headers,
        body: body !== undefined ? JSON.stringify(body) : undefined,
    })

    // token missing/expired on a protected call -> back to login
    if (res.status === 401 && auth) {
        clearAuth()
        window.location.href = '/login'
        throw new Error('Session expired, please log in again')
    }

    if (!res.ok) {
        let message = `Request failed (${res.status})`
        try {
            const data = await res.json()
            if (data.errors) {
                message = Object.entries(data.errors).map(([f, m]) => `${f}: ${m}`).join(', ')
            } else if (data.detail) {
                message = data.detail
            }
        } catch {
            // body wasn't JSON (e.g. Traefik's plain-text 404)
        }
        throw new Error(message)
    }

    return res.status === 204 ? null : res.json()
}