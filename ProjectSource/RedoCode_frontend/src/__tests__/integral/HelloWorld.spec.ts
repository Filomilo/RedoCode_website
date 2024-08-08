import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import HelloWorld from '@/components/HelloWorld.vue'

describe('HelloWorld intergrational', () => {
  it("2+2",()=>{
    expect(2+2===4,"2=2equal")
  })
})
