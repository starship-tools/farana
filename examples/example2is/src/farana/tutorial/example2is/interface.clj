(ns farana.tutorial.example2is.interface
        "This is namespace is for all project interfaces. This is used in the
        example as one possible way -- of several -- to organize interfaces in
        projects.

        For a single, large interface, we might have used `:gen-interface` in the
        namespace declaration. For one or more very short, simple interfaces, we
        could have skipped a separate file altogether and used `definterface` in
        the `core.clj` file.")

(definterface IDictionary
  (^Boolean checkWord [^String word]))
