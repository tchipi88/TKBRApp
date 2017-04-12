(function() {
    'use strict';

    angular
        .module('app')
        .controller('CommandeLigneDialogController', CommandeLigneDialogController);

    CommandeLigneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CommandeLigne','Commande','Produit'];

    function CommandeLigneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, CommandeLigne ,Commande,Produit) {
        var vm = this;

        vm.commandeLigne = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.commandes = Commande.query();
vm.produits = Produit.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commandeLigne.id !== null) {
                CommandeLigne.update(vm.commandeLigne, onSaveSuccess, onSaveError);
            } else {
                CommandeLigne.save(vm.commandeLigne, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:commandeLigneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
