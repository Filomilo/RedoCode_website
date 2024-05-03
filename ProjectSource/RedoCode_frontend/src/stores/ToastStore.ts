import { defineStore } from 'pinia'
import { ref , computed } from 'vue'
import { useToast } from 'primevue/usetoast';

export const useToastStore = defineStore('toastStore', () => {

    const toast = useToast();

    const shotWrongLogin=()=>{
        toast.add({ severity: 'error', summary: 'Info', detail: 'Wrong e-mail or passowrd', life: 2000 });
    }
    const shotCorrectLogin=()=>{
        toast.add({ severity: 'success', summary: 'Info', detail: 'signed in', life: 2000 });
    }

  return { shotWrongLogin,
    shotCorrectLogin
   }
})
