import { Injectable, signal } from "@angular/core";
import { delay, Observable, of } from "rxjs";


@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private readonly storageKey = 'isLoggedIn';
    readonly #isLoggedIn = signal(false);
    readonly isLoggedIn = this.#isLoggedIn.asReadonly();
   
    constructor() {
        const stored = localStorage.getItem(this.storageKey);
        this.#isLoggedIn.set(stored === 'true');
    }
   
    login(name: string, password: string): Observable<boolean> {
        const isLoggedIn= name === 'pikachu' && password === 'pikachu#';

        this.#isLoggedIn.set(isLoggedIn);
        localStorage.setItem(this.storageKey, String(isLoggedIn));
        return of(isLoggedIn).pipe(delay(1000));
    }
}
