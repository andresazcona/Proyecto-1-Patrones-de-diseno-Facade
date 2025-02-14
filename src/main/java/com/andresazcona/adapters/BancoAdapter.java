package com.andresazcona.adapters;

import com.andresazcona.domain.Transaccion;
import java.io.File;
import java.util.List;

public interface BancoAdapter {
    List<Transaccion> extraerDatos(File archivo) throws Exception;
}