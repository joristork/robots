ram char * ram_ptr;
near rom char * near_rom_ptr;
far rom char * far_rom_ptr;

char ram_array[] = "ram";
rom char rom_array[] = "ROM";

void main (void)
{
  ram_ptr = &ram_array[0];
  near_rom_ptr = &rom_array[0];
  far_rom_ptr = (far rom char *)&rom_array[0];

  while (1)
    ;
}
