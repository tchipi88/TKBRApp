(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitCategorieController', ProduitCategorieController);

    ProduitCategorieController.$inject = ['ProduitCategorie', 'ProduitCategorieSearch'];

    function ProduitCategorieController(ProduitCategorie, ProduitCategorieSearch) {

        var vm = this;

        vm.produitCategories = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ProduitCategorie.query(function(result) {
                vm.produitCategories = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProduitCategorieSearch.query({query: vm.searchQuery}, function(result) {
                vm.produitCategories = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
