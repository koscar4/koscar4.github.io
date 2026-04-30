import { Directive, ElementRef, HostListener, input } from '@angular/core';
import { getPokemmonColor } from './pokemon.model';

@Directive({
  selector: '[appPokemonBorder]',
})
export class PokemonBorderDirective {
  private intitialcolor: string;
  pokemonType= input.required<string>();

  constructor(private el: ElementRef) {
    this.intitialcolor = this.el.nativeElement.style.borderColor;
    this.el.nativeElement.style.borderWidth = '2px';
   }
  @HostListener('mouseenter') onMouseEnter() {
    const color = getPokemmonColor(this.pokemonType());
    this.setBorder(color);
}
@HostListener('mouseleave') onMouseLeave() {
    this.setBorder(this.intitialcolor);
    const color =  this.intitialcolor;
}
private setBorder(color: string) {
    this.el.nativeElement.style.borderColor = color;
}
 
}